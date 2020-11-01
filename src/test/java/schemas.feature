Feature: Prepare schemas
	Scenario: Prepare schemas
		* def schemas = {}

		* set schemas.unit =
		"""
		{
            id: '##number',
            singular: '##string',
            plural: '##string',
            abbreviation: '##string'
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

		* set schemas.food =
		"""
		{
			id: '##number',
			fdcId: '##string',
			description: '#string',
			brandOwner: '##string',
			ingredients: '##string',
			ssrDisplayUnit: '##(schemas.unit)',
			csrDisplayUnit: '##(schemas.unit)',
			nutrients: '##[] schemas.nutrient',
			conversionRatios: '##[] schemas.conversionRatio'
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