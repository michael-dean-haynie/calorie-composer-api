Feature: Prepare schemas
	Scenario: Prepare schemas
		* def schemas = {}

		# unit.draft should also be unit but seems can't nest recursively like that
		* set schemas.unit =
		"""
		{
            id: '##number',
            isDraft: '##boolean',
            singular: '##string',
            plural: '##string',
            abbreviation: '##string',
            draft: '#ignore'
		}
		"""

		# ---------------------------------------------
		# Food
		# ---------------------------------------------

		* set schemas.nutrient =
		"""
		{
            id: '##number',
            name: '##string',
            unit: '##(schemas.unit)',
            amount: '##number'
		}
		"""

		* set schemas.conversionRatio =
		"""
		{
            id: '##number',
			amountA: '##number',
            unitA: '##(schemas.unit)',
            freeFormValueA: '##string',
            amountB: '##number',
            unitB: '##(schemas.unit)',
            freeFormValueB: '##string'
		}
		"""

		# food.draft should also be a food but seems can't nest recursively like that
		* set schemas.food =
		"""
		{
			id: '##number',
			isDraft: '##boolean',
			fdcId: '##string',
			description: '##string',
			brandOwner: '##string',
			ingredients: '##string',
			ssrDisplayUnit: '##(schemas.unit)',
			csrDisplayUnit: '##(schemas.unit)',
			nutrients: '##[] schemas.nutrient',
			conversionRatios: '##[] schemas.conversionRatio',
			draft: '#ignore'
		}
		"""

		# ---------------------------------------------
		# Combo Food
		# ---------------------------------------------

		* set schemas.comboFoodFoodAmount =
		"""
		{
			id: '##number',
			food: '#(schemas.food)',
			unit: '##string',
			scalar: '##number',
		}
		"""

		* set schemas.comboFoodPortion =
		"""
		{
			id: '##number',
			isFoodAmountRefPortion: '#boolean',
            isServingSizePortion: '#boolean',
			metricUnit: '##string',
            metricScalar: '##number',
            householdUnit: '##string',
            householdScalar: '##number'
		}
		"""

		* set schemas.comboFood =
		"""
		{
			id: '##number',
			isDraft: '#boolean',
			description: '##string',
			foodAmounts: '##[] schemas.comboFoodFoodAmount',
			portions: '##[] schemas.comboFoodPortion'
		}
		"""